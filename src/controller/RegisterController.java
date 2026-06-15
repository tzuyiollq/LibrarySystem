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
                    frame.getTxtStudentNo().getText().trim();

            String name =
                    frame.getTxtName().getText().trim();

            String password =
                    new String(
                            frame.getTxtPassword().getPassword()
                    ).trim();

            String role =
                    frame.getCmbRoleLevel()
                            .getSelectedItem()
                            .toString();

            if (studentNo.isEmpty()
                    || name.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "請完整填寫學號、姓名與密碼。"
                );

                return;
            }

            boolean success =
                    authService.registerUser(
                            studentNo,
                            name,
                            password,
                            role
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        frame,
                        "註冊成功！請返回登入。"
                );

                frame.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "註冊失敗，可能此學號已經註冊過。"
                );
            }
        });

        frame.getBtnBack().addActionListener(e -> {
            frame.dispose();
        });
    }
}