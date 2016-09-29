$(function() {
	
	$('#employeeForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			//验证的是控件上的name属性的名称
			username : {
				validators : {
					notEmpty : true,
					stringLength: {
                        min: 4,
                        max: 30
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '请输入正确格式的用户名:只能是英文或者数字或者_'
                    },
                    remote : {
						url : '/employee_checkUsername.action?id='
								+ $("#id").val(),
						message : '用户名已经存在！'
					}
				}
			},
			password: {
                validators: {
                    notEmpty: true,
                    different: {
                        field: 'username',
                        message: '用户名和密码不能一致'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: true,
                    identical: {
                        field: 'password',
                        message: '密码和确认密码必须一致'
                    }
                }
            },
            age: {
                validators: {
                    notEmpty: true,
                    between: {
						min : 18,
						max : 80
					}
                }
            },
			email: {
                validators: {
                    emailAddress: true
                }
            }
		}
	});
});
