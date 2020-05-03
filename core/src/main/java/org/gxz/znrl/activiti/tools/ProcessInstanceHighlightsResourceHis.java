package org.gxz.znrl.activiti.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.rest.common.api.ActivitiUtil;
import org.activiti.rest.common.api.SecuredResource;
import org.restlet.resource.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ProcessInstanceHighlightsResourceHis extends SecuredResource {
    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceHighlightsResourceHis.class);
    private RuntimeService runtimeService = ActivitiUtil.getRuntimeService();
    private RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) ActivitiUtil.getRepositoryService();
    private HistoryService historyService = (HistoryService) ActivitiUtil.getHistoryService();
    private HistoricProcessInstanceEntity historicProcessInstanceEntity;
    private ProcessDefinitionEntity processDefinition;


    protected ObjectMapper objectMapper = new ObjectMapper();

    @Get("json")
    public ObjectNode getHighlighted() {
        String processInstanceId = (String) getRequest().getAttributes().get("processInstanceId");

        if (processInstanceId == null) {
            throw new ActivitiException("No process instance id provided");
        }

        ObjectNode responseJSON = objectMapper.createObjectNode();

        responseJSON.put("processInstanceId", processInstanceId);

        ArrayNode activitiesArray = objectMapper.createArrayNode();
        ArrayNode flowsArray = objectMapper.createArrayNode();

        try {
            historicProcessInstanceEntity = (HistoricProcessInstanceEntity)historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinition = (ProcessDefinitionEntity) repositoryService.getDeployedProcessDefinition(historicProcessInstanceEntity.getProcessDefinitionId());

            responseJSON.put("processDefinitionId", historicProcessInstanceEntity.getProcessDefinitionId());
            List<String> highLightedActivities = new ArrayList<>();
            try {
                highLightedActivities = runtimeService.getActiveActivityIds(processInstanceId);
            }
            catch (Exception e)
            {
                logger.debug("-------historic---------");
                //e.printStackTrace();
            }
            finally {
                if(highLightedActivities.size()==0)
                {
                    highLightedActivities = getLastFlow(processDefinition);
                }
            }
            List<String> highLightedFlows = getHighLightedFlows(processDefinition, processInstanceId);

            for (String activityId : highLightedActivities) {
                activitiesArray.add(activityId);
            }

            for (String flow : highLightedFlows) {
                flowsArray.add(flow);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        responseJSON.put("activities", activitiesArray);
        responseJSON.put("flows", flowsArray);

        return responseJSON;
    }

    private List<String> getLastFlow(ProcessDefinitionEntity processDefinition) {
        List<ActivityImpl> activitiList = processDefinition.getActivities();
        List<String> result = new ArrayList<>();
        String id = "";
        for (ActivityImpl activityImpl : activitiList) {
            id = activityImpl.getId();
            logger.debug("-------getLastFlow---------"+id);
        }
        result.add(id);
        return result;
    }
    /**
     * getHighLightedFlows
     *
     * @param processDefinition
     * @param processInstanceId
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition, String processInstanceId) {

        List<String> highLightedFlows = new ArrayList<String>();

        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                        //order by startime asc is not correct. use default order is correct.
                        //.orderByHistoricActivityInstanceStartTime().asc()/*.orderByActivityId().asc()*/
                .list();

        LinkedList<HistoricActivityInstance> hisActInstList = new LinkedList<HistoricActivityInstance>();
        hisActInstList.addAll(historicActivityInstances);

        getHighlightedFlows(processDefinition.getActivities(), hisActInstList, highLightedFlows);

        return highLightedFlows;
    }

    /**
     * getHighlightedFlows
     *
     * code logic:
     * 1. Loop all activities by id asc order;
     * 2. Check each activity's outgoing transitions and eventBoundery outgoing transitions, if outgoing transitions's destination.id is in other executed activityIds, add this transition to highLightedFlows List;
     * 3. But if activity is not a parallelGateway or inclusiveGateway, only choose the earliest flow.
     *
     * @param activityList
     * @param hisActInstList
     * @param highLightedFlows
     */
    private void getHighlightedFlows(List<ActivityImpl> activityList, LinkedList<HistoricActivityInstance> hisActInstList, List<String> highLightedFlows){

        //check out startEvents in activityList
        List<ActivityImpl> startEventActList = new ArrayList<ActivityImpl>();
        Map<String, ActivityImpl> activityMap = new HashMap<String, ActivityImpl>(activityList.size());
        for(ActivityImpl activity : activityList){

            activityMap.put(activity.getId(), activity);

            String actType = (String) activity.getProperty("type");
            if (actType != null && actType.toLowerCase().indexOf("startevent") >= 0){
                startEventActList.add(activity);
            }
        }

        //These codes is used to avoid a bug:
        //ACT-1728 If the process instance was started by a callActivity, it will be not have the startEvent activity in ACT_HI_ACTINST table
        //Code logic:
        //Check the first activity if it is a startEvent, if not check out the startEvent's highlight outgoing flow.
        HistoricActivityInstance firstHistActInst = hisActInstList.getFirst();
        String firstActType = (String) firstHistActInst.getActivityType();
        if (firstActType != null && firstActType.toLowerCase().indexOf("startevent") < 0){
            PvmTransition startTrans = getStartTransaction(startEventActList, firstHistActInst);
            if (startTrans != null){
                highLightedFlows.add(startTrans.getId());
            }
        }

        while (hisActInstList.size() > 0) {
            HistoricActivityInstance histActInst = hisActInstList.removeFirst();
            ActivityImpl activity = activityMap.get(histActInst.getActivityId());
            if (activity != null) {
                boolean isParallel = false;
                String type = histActInst.getActivityType();
                if ("parallelGateway".equals(type) || "inclusiveGateway".equals(type)){
                    isParallel = true;
                } else if ("subProcess".equals(histActInst.getActivityType())){
                    getHighlightedFlows(activity.getActivities(), hisActInstList, highLightedFlows);
                }

                List<PvmTransition> allOutgoingTrans = new ArrayList<PvmTransition>();
                allOutgoingTrans.addAll(activity.getOutgoingTransitions());
                allOutgoingTrans.addAll(getBoundaryEventOutgoingTransitions(activity));
                List<String> activityHighLightedFlowIds = getHighlightedFlows(allOutgoingTrans, hisActInstList, isParallel);
                highLightedFlows.addAll(activityHighLightedFlowIds);
            }
        }
    }

    /**
     * Check out the outgoing transition connected to firstActInst from startEventActList
     *
     * @param startEventActList
     * @param firstActInst
     * @return
     */
    private PvmTransition getStartTransaction(List<ActivityImpl> startEventActList, HistoricActivityInstance firstActInst){
        for (ActivityImpl startEventAct: startEventActList) {
            for (PvmTransition trans : startEventAct.getOutgoingTransitions()) {
                if (trans.getDestination().getId().equals(firstActInst.getActivityId())) {
                    return trans;
                }
            }
        }
        return null;
    }

    /**
     * getBoundaryEventOutgoingTransitions
     *
     * @param activity
     * @return
     */
    private List<PvmTransition> getBoundaryEventOutgoingTransitions(ActivityImpl activity){
        List<PvmTransition> boundaryTrans = new ArrayList<PvmTransition>();
        for(ActivityImpl subActivity : activity.getActivities()){
            String type = (String)subActivity.getProperty("type");
            if(type!=null && type.toLowerCase().indexOf("boundary")>=0){
                boundaryTrans.addAll(subActivity.getOutgoingTransitions());
            }
        }
        return boundaryTrans;
    }

    /**
     *
     * @param pvmTransitionList
     * @param hisActInstList
     * @param isParallel
     * @return
     */
    private List<String> getHighlightedFlows(List<PvmTransition> pvmTransitionList, LinkedList<HistoricActivityInstance> hisActInstList, boolean isParallel){

        List<String> highLightedFlowIds = new ArrayList<String>();

        PvmTransition earliestTrans = null;
        HistoricActivityInstance earliestHisActInst = null;

        for (PvmTransition pvmTransition : pvmTransitionList) {

            String destActId = pvmTransition.getDestination().getId();
            HistoricActivityInstance destHisActInst = findHisActInst(hisActInstList, destActId);
            if (destHisActInst != null) {
                if (isParallel) {
                    highLightedFlowIds.add(pvmTransition.getId());
                } else if (earliestHisActInst == null || (earliestHisActInst.getId().compareTo(destHisActInst.getId()) > 0)) {
                    earliestTrans = pvmTransition;
                    earliestHisActInst = destHisActInst;
                }
            }
        }

        if ((!isParallel) && earliestTrans!=null){
            highLightedFlowIds.add(earliestTrans.getId());
        }

        return highLightedFlowIds;
    }

    private HistoricActivityInstance findHisActInst(LinkedList<HistoricActivityInstance> hisActInstList, String actId){
        for (HistoricActivityInstance hisActInst : hisActInstList){
            if (hisActInst.getActivityId().equals(actId)){
                return hisActInst;
            }
        }
        return null;
    }
}
