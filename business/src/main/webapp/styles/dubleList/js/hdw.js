// JavaScript Document
$(document).ready(function () {

    $("#add").click(function () {

        $("#unExistSelect option:selected").appendTo("#existSelect");
        $("#existSelect option").attr("selected",false);
    });

    $("#add_all").click(function () {

        $("#unExistSelect option").appendTo("#existSelect");
        $("#existSelect option").attr("selected",false);

    });

    $("#remove").click(function () {

        $("#existSelect option:selected").appendTo("#unExistSelect");
        $("#unExistSelect option").attr("selected",false);

    });

    $("#remove_all").click(function () {

        $("#existSelect option").appendTo("#unExistSelect");
        $("#unExistSelect option").attr("selected",false);

    });

    $("#existSelect").dblclick(function () {

        $("#existSelect option:selected").appendTo("#unExistSelect");
        $("#unExistSelect option").attr("selected",false);

    });

    $("#unExistSelect").dblclick(function () {

        $("#unExistSelect option:selected").appendTo("#existSelect");
        $("#existSelect option").attr("selected",false);
    });


});