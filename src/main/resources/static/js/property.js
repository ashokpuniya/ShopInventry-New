function getKey() {
    var data = {
        propertyName: $('#text_property_name').val()
    };
    // alert("data " + data.propertyName);
    if (data.propertyName === "") {
        $("#df_Message").html("Please fill out this field.").addClass("col-lg-12 alert alert-danger");
        setTimeOut();
    } else {
        $.ajax({
            url: "/admin/searchKey",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (response) {
                if (response.successful) {
                    alert(JSON.stringify(response));
                    $("#pannel").show();
                    $("#text_id").val(response.property.id);
                    $('#text_property_name1').val(response.property.propertyName);
                    $('#text_property_value').val(response.property.propertyValue);
                    $('#text_UpdatedBy').val(response.property.updatedBy);
                    $(".input_disabled_id").prop('disabled', true);
                    $("#add").hide();
                    $("#update").hide();
                    $("#edit").show();
                } else {
                    // alert(JSON.stringify(response.message));
                    $("#df_Message").html(response.message).addClass("col-lg-12 alert alert-danger");
                    setTimeOut();
                }
            }
        });
    }

}

function editProperty() {
    $("#add").hide();
    $("#edit").hide();
    $("#update").show();
    $(".input_disabled_id").prop('disabled', false);

}

function setTimeOut() {
    setTimeout(function () {// wait for 5 secs(2)
        location.reload(); // then reload the page.(3)
    }, 1500);
}

function showDiv() {
    $("#pannel").show();
}