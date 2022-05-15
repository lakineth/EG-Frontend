$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});
//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	 $("#alertSuccess").text("");
	 $("#alertSuccess").hide();
	 $("#alertError").text("");
	 $("#alertError").hide(); 
	 
	// Form validation-------------------
	 var status = validateUserForm();
	 if (status != true)
		 {
		  $("#alertError").text(status);
		  $("#alertError").show();
		  return;
	 }
	 //If status equals to true
	 var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT";
	 var formData = new FormData($("#formUsers")[0]);
	 console.log(formData);
	 $.ajax(
	 {
		 url : "UsersAPI",
		 type : type,
		 data : formData,
		 enctype : "multipart/form-data",
		 complete : function(response, status)
		 {
			 onUserSaveComplete(response.responseText, status);
		 },
		 processData : false,
		 contentType :false
	 }); 
});

function onUserSaveComplete(response, status)
{
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divUsersGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
	 } else if (status == "error")
	 {
			 $("#alertError").text("Error while saving.");
			 $("#alertError").show();
	 } else
	 {
			 $("#alertError").text("Unknown error while saving..");
			 $("#alertError").show();
	 } 
	
	 $("#hidUserIDSave").val("");
	 $("#formUsers")[0].reset();
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	 $("#hidUserIDSave").val($(this).data("userid")); 
	 $("#username").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#address").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#phonenum").val($(this).closest("tr").find("td:eq(3)").text());
	 $("#email").val($(this).closest("tr").find('td:eq(4)').text());
	 $("#password").val($(this).closest("tr").find('td:eq(5)').text());
});

function validateUserForm()
{
	// username
	if ($("#username").val().trim() == "")
	{
		return "Insert User Name.";
	}
	// address
	if ($("#address").val().trim() == "")
	{
		return "Insert Address.";
	}
	// phonenum
	if ($("#phonenum").val().trim() == "")
	{
		return "Insert Phone Number.";
	}
	// email
	if ($("#email").val().trim() == "")
	{
		return "Insert Email.";
	}
	// password
	if ($("#password").val().trim() == "")
	{
		return "Insert Password.";
	}
	
	return true;
}

$(document).on("click", ".btnRemove", function(event)
		{
				$.ajax(
				 {
					 url : "UsersAPI",
					 type : "DELETE",
					 data : "userID=" + $(this).data("userid"),
					 dataType : "text",
					 complete : function(response, status)
					 {
						 onUserDeleteComplete(response.responseText, status);
					 }
				 });
		});


function onUserDeleteComplete(response, status)
{
	if (status == "success")
	 {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 $("#divUsersGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		}
	 } else if (status == "error")
	 {
			 $("#alertError").text("Error while deleting.");
			 $("#alertError").show();
	 } else
	 {
			 $("#alertError").text("Unknown error while deleting..");
			 $("#alertError").show();
	 }
}


