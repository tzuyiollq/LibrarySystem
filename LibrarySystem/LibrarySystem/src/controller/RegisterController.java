package controller;

import javax.swing.JOptionPane;

import service.AuthService;
import view.RegisterFrame;

public class RegisterController {

    private RegisterFrame frame;
    private AuthService authService;

    public RegisterController(RegisterFrame frame) {

        this.frame = frame;
        this.authService = new AuthService();

        initEvents();
    }

    private void initEvents() {

    	frame.getBtnRegister().addActionListener(e -> {

    	    String studentNo =
    	            frame.getTxtStudentNo()
    	                 .getText()
    	                 .trim();

    	    String name =
    	            frame.getTxtName()
    	                 .getText()
    	                 .trim();

    	    String password =
    	            new String(
    	                    frame.getTxtPassword()
    	                         .getPassword()
    	            ).trim();

    	    String role =
    	            frame.getCmbRole()
    	                 .getSelectedItem()
    	                 .toString();

    	    // 空白檢查
    	    if (studentNo.isEmpty()
    	            || name.isEmpty()
    	            || password.isEmpty()) {

    	        JOptionPane.showMessageDialog(
    	                frame,
    	                "學號、姓名、密碼不得空白！"
    	        );

    	        return;
    	    }
    	    if (authService.existsStudentNo(studentNo)) {

    	        JOptionPane.showMessageDialog(
    	                frame,
    	                "此學號已經註冊！"
    	        );

    	        return;
    	    }

    	    authService.registerUser(
    	            studentNo,
    	            name,
    	            password,
    	            role
    	    );

    	    JOptionPane.showMessageDialog(
    	            frame,
    	            "註冊成功！"
    	    );
    	});

        frame.getBtnBack().addActionListener(e -> {

            frame.dispose();
        });
    }
}