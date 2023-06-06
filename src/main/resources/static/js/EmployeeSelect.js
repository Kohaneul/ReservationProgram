
   let employee_id = document.getElementById('employeeIdSelect').value;

	function radioCheck(employeeId){
		employee_id= employeeId;
	}



    function closeAndSendParam()
    {
    if(employee_id == null || employee_id == "" ||employee_id == "undefined"){
        alert('선택하지 않았습니다');
        window.location.reload();
    }
    else { open("/reservation/info/employee/"+employee_id); self.close(); }
    }

