<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
<script src="<c:url value="/resources/jquery.js" />"></script>
<script src="<c:url value="/resources/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/jquery-ui-timepicker-addon.js" />"></script>

<link href="<c:url value="/resources/jquery-ui.css" />" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/resources/jquery-ui-timepicker-addon.css" />" rel="stylesheet" type="text/css"/>


<style>

body,
input,
textarea { font-family: Arial; font-size: 14; }

.list-odd { background-color:#ecf9ec; }
.list-even { background-color:#d9f2d9; }

.button {
    background-color:#b3e6b3; 
    border: none;
    padding: 5px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

.list-odd:hover,
.list-even:hover
{
	background-color:#c6ecc6; 
	cursor: pointer;
}



.button:hover {
	background-color:#8cd98c; 
	cursor: pointer;
}

.f2ieldset-auto-width {
	display: inline-block;
    }

</style>
<div id=main style="min-width:400px; max-width:600px; margin: 0 auto;">
<table cellpadding=5 cellspacing=0 width=100%>
<tr><td>
<br>
<h3><spring:message code='main.title' text='Service for storing and searching notes' /> </h3>
<spring:message code='main.lang' text='Language' /> : 
<a href="?lang=en">English</a> 
| 
<a href="?lang=ru">Русский</a>
</td></tr>
<tr><td>
	    <div id=new>
	    <fieldset>
		<legend><spring:message code='legend.create' text='Create new note' /></legend>	
		<input id="subjectNew" type="text" value="<spring:message code='main.subject' text='Subject' />" style="width:450px;" size="50" /><br>
		<textarea id="textNew" style="width:450px;" rows="5"><spring:message code='main.text' text='Text' /></textarea>
		<br>
		<input id="sendNew" type="button" class="button" value="<spring:message code='button.create' text='Create' />" />
		<br>
	    </fieldset>
	    </div>
	    <div id=filter>
	    <fieldset>
		<legend><spring:message code='legend.filter' text='Filter notes' /></legend>	
		<input id="inputFilter" type="text" value="" style="width:450px;" size="50" /><br>
		<input id="sendFilter" type="button" class="button" value="<spring:message code='button.apply' text='Apply' />" />
		<br>
	    </fieldset>
	    </div>
	    <div id=edit>
	    <fieldset>
		<legend><spring:message code='legend.edit' text='Edit note' /></legend>	
		<spring:message code='item.created' text='created' />: <b><span id="createTimeEdit"></span></b><br> 
		<spring:message code='item.modified' text='modified' />: <b><span id="modifyTimeEdit"></span></b><br>
		<input id="idEdit" type="hidden" />
		<input id="subjectEdit" type="text" style="width:450px;" size="50" value=""/><br>
		<textarea id="textEdit" style="width:450px;" rows="5"> </textarea>
		<br>
		<input id="sendEdit" type="button" class="button" value="<spring:message code='button.save' text='Save' />" />
		<input id="cancelEdit" type="button" class="button" value="<spring:message code='button.cancel' text='Cancel' />" />
		<br>
	    </fieldset>
	    </div>

	    <div id=findTopWords>
	    <fieldset class="fieldset-auto-width">
		<legend><spring:message code='legend.find' text='Find 5 top most used words' /></legend>	
		<spring:message code='main.startPeriod' text='Start time' />:  
		<input id="startPeriod" class="timepicker" type="text" />
		<spring:message code='main.endPeriod' text='End time' />:  
		<input id="endPeriod" class="timepicker" type="text" />
		<br>
		<input id="sendFind" type="button" class="button" value="<spring:message code='button.find' text='Find Top Words' />" /><br>
		<spring:message code='main.topWords' text='Top Words' />: <b><span id='resultFind'></span></b>
		<br>
	    </fieldset>
	    </div>

</td></tr>

</table>


<table cellpadding=5 cellspacing=0 id=list width=100%>

    <c:forEach var="note" items="${listNote}">
	<tr id="note${note.id}"><td>
		<spring:message code='item.created' text='created' />: <b><span name="createTime" time="${note.createTime.time}"></span></b> 
		<spring:message code='item.modified' text='modified' />: <b><span name="modifyTime" time="${note.modifyTime.time}"></span></b>
		<br>
		<b><span name="subject">${note.subject}</span></b><br>
		<span name="text">${note.text}</span>
	</td><td width=10%> 
		<input type="button" class="button" value="<spring:message code='button.edit' text='Edit' />" onclick="editNote('${note.id}');" />
		<input type="button" class="button" value="<spring:message code='button.delete' text='Delete' />" onclick="deleteNote('${note.id}');" />

	</td></tr>

    </c:forEach>

</table>
</div>
<div id=edit style="min-width:400px; max-width:600px; margin: 0 auto;">
</div>
<script type="text/javascript">

    function updateTableStyle() {
	$("#list tr:odd").removeClass("list-even").addClass("list-odd");
	$("#list tr:even").removeClass("list-odd").addClass("list-even");
    }	

    $("span[time]").each(function() {$(this).text(timeToString($(this).attr("time")))});
    updateTableStyle();
    $("#edit").hide();

    function timeToString(time) {

        time=parseInt(time);
    	var date = new Date(time);
	var d = date.getDate();
	var m = date.getMonth() + 1;
	var y = date.getFullYear();
	var hh = date.getHours();
	var mm = date.getMinutes();
	var ss = date.getSeconds();

    	if (d   < 10) {d = "0"+d;}
    	if (m   < 10) {m = "0"+m;}
    	if (hh   < 10) {hh = "0"+hh;}
    	if (mm < 10) {mm = "0"+mm;}
    	if (ss < 10) {ss = "0"+ss;}

   	return y+'/'+m+'/'+d+' '+hh+':'+mm+':'+ss;
    }

    function note2Html(note) {
		return 	"<tr id='note"+
			note.id +
			"'><td> <spring:message code='item.created' text='created' />: <b><span name='createTime'>" +
			timeToString(note.createTime) +
			"</span></b> <spring:message code='item.modified' text='modified' />: <b><span name='modifyTime'>" +
			timeToString(note.modifyTime) +
			"</span></b><br><b><span name='subject'>" +
			note.subject + 
			"</span></b><br><span name='text'>" +
			note.text + 
			"</span></td><td width=10%> \
			<input type='button' class='button' value=\"<spring:message code='button.edit' text='Edit' />\" onclick='editNote(\""+note.id+"\");' /> \
			<input type='button' class='button' value=\"<spring:message code='button.delete' text='Delete' />\"  onclick='deleteNote(\""+note.id+"\");'  /> \
			</td></tr>";
    }		

    function outputListNote(listNote) {
	var listAsString="";
	$.each(listNote, function(id, note) {
		listAsString = listAsString + note2Html(note);
       	});
        $("#list").html(listAsString);
	updateTableStyle();
    }		

    function deleteNote(index) {
	//alert("deleteNote " + index);
        $.ajax({
            type: "POST",
            url: "<c:url value="/deleteNote" />",
	    data: { "id":index },
	    dataType : 'text', 
            success: function(result) {
		$("#note"+index).remove();
		updateTableStyle();
            },
            complete: function(data,status)  {
                alert(status + ": " + data.statusText );
            }
        });

    }

    function editNote(index) {
	//alert("editNote " + index);
	var $note=$("#note"+index);
        $("#idEdit").val(index);
        $("#createTimeEdit").text($note.find("span[name='createTime']").text());
        $("#modifyTimeEdit").text($note.find("span[name='modifyTime']").text());
        $("#subjectEdit").val($note.find("span[name='subject']").text());
	$("#textEdit").val($note.find("span[name='text']").text());

	$("#edit").show();
	$("#new").hide();
	$("#filter").hide();
	$("#findTopWords").hide();
	$("#list").hide();
    }

    $("#sendEdit").click(function(){
        var index = $("#idEdit").val();
        var note = { "id": index, "subject": $("#subjectEdit").val(), "text": $("#textEdit").val() };
        //alert("sendEdit click" + note.id);   
        $.ajax({
            type: "POST",
            url: "<c:url value="/updateNote" />",
            data: JSON.stringify(note),
            contentType: "application/json; charset=utf-8",
            success: function(note) {
		//alert("result " + JSON.stringify(note));
		var $note=$("#note"+index);
        	$note.find("span[name='createTime']").text(timeToString(note.createTime));
		$note.find("span[name='modifyTime']").text(timeToString(note.modifyTime));
        	$note.find("span[name='subject']").text(note.subject);
		$note.find("span[name='text']").text(note.text);
            },
            error: function(data,status)  {
                alert(status + ": " + data.statusText );
            },
            complete: function(data,status)  {
		$("#edit").hide();
		$("#new").show();
		$("#filter").show();
		$("#findTopWords").show();
		$("#list").show();
            }

        });
   });

    $("#cancelEdit").click(function(){
		$("#edit").hide();
		$("#new").show();
		$("#filter").show();
		$("#findTopWords").show();
		$("#list").show();
    });

    $("#sendNew").click(function(){

        var note = { "subject": $("#subjectNew").val(), "text": $("#textNew").val() };
        //alert("sendNew click");   
        $.ajax({
            type: "POST",
            url: "<c:url value="/addNote" />",
            data: JSON.stringify(note),
            contentType: "application/json; charset=utf-8",
            success: function(result) {
		//alert("result " + JSON.stringify(result));
	        $("#list").append(note2Html(result));
                updateTableStyle();
            },
            complete: function(data,status)  {
                alert(status + ": " + data.statusText );
            }
        });
   });

    $("#sendFilter").click(function(){

        $.ajax({
            type: "POST",
            url: "<c:url value="/filterNote" />",
	    data: { "filter" : $("#inputFilter").val() } , 
	    dataType: "json",
            success: function(result) {
		//alert("result " + JSON.stringify(result));
                outputListNote(result);
            },
            complete: function(data,status)  {
                alert(status + ": " + data.statusText );
            }
        });

   });

   $('input.timepicker').
	datetimepicker({
		controlType: 'select',
		oneLine: true,
		dateFormat: 'dd/mm/yy',
		timeFormat: 'HH:mm:ss'
   	}).
	datetimepicker("setDate",new Date());

    $('#sendFind').click(function(){

	var epochStartPeriod = new Date( $("#startPeriod").datetimepicker( "getDate" ) ).getTime();
	var epochEndPeriod = new Date( $("#endPeriod").datetimepicker( "getDate" ) ).getTime();
        $.ajax({
            type: "POST",
            url: "<c:url value="/findTopWords" />",
	    data: { "startPeriod": epochStartPeriod,"endPeriod":epochEndPeriod }, 
	    dataType: "json",
            success: function(result) {
		if (result.length == 0) {
			$("#resultFind").html("<span style='color:red;'>not found</span>");
		} else {
       			$("#resultFind").html(result.join(" "));
		}
            },
            complete: function(data,status)  {
                alert(status + ": " + data.statusText );
            }
        });
   });


</script>
