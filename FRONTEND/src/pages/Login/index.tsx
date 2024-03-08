import React, { useContext, useRef } from "react";

import { ReactComponent as RoundedUser } from "../../resources/icons/rounded_user.svg";
import { ReactComponent as Lock } from "../../resources/icons/lock.svg";

import axios from "axios";
import { AlertsContext } from "../../App";
import LabeledButton from "../../components/Buttons/LabeledButton";

type TUserCredentials = {
    email: string;
    password: string;
};

const LoginPage: React.FC = () => {
    const { addToast } = useContext(AlertsContext);

    const inEmail = useRef<HTMLInputElement | null>(null);
    const inPassword = useRef<HTMLInputElement | null>(null);

    const validateFields = (userCredentials: TUserCredentials) => {
        let isValid = true;

        if (userCredentials.email.trim() === "") {
            inEmail.current?.classList.add("is-invalid");
            if (isValid) isValid = false;
        } else if (inEmail.current?.classList.contains("is-invalid")) inEmail.current?.classList.remove("is-invalid");

        if (userCredentials.password.trim() === "") {
            inPassword.current?.classList.add("is-invalid");
            if (isValid) isValid = false;
        } else if (inPassword.current?.classList.contains("is-invalid")) inPassword.current?.classList.remove("is-invalid");

        return isValid;
    };

    const logInUser = (userCredentials: TUserCredentials) => {
        axios
            .post("http://localhost:9090/auth/login", {
                email: userCredentials.email,
                password: userCredentials.password,
            })
            .then((response) => {
                if (response.status >= 200 && response.status <= 299) {
                    const data = response.data;
                    const authType = data.type;
                    const authToken = data.token;
                    window.sessionStorage.setItem("user_auth", `${authType} ${authToken}`);
                    window.sessionStorage.setItem("user_type", data.roles[data.roles.length - 1]);

                    addToast({
                        header: "Sucesso ao entrar!",
                        content: "Você será redirecionado.",
                        isSuccess: true,
                    });

                    setTimeout(() => {
                        if (data.roles.includes("ROLE_REQUESTING_MANAGER")) window.location.href = "/voce/grupos-e-representantes";
                        else if(data.roles.includes("ROLE_MIDDLE_MANAGER")) window.location.href = "/admin/solicitacoes";
                        else if(data.roles.includes("ROLE_MIDDLE_EMPLOYEE")) window.location.href = "/inter/solicitacoes";
                        else window.location.href = "/voce/solicitacoes";
                    }, 2000);
                }
            })
            .catch(() => {
                addToast({
                    header: "Error!",
                    content: "Usuário e/ou senha incorreto(s).",
                    isSuccess: false,
                });
            });
    };

    const handleUserLogin = (e: React.FormEvent) => {
        e.preventDefault();

        const userCredentials: TUserCredentials = {
            email: inEmail.current?.value || "",
            password: inPassword.current?.value || "",
        };

        if (validateFields(userCredentials)) logInUser(userCredentials);
    };

    return (
        <div id="login-page" className="page d-flex align-items-center justify-content-center flex-column">
            <h1 className="main-text main-color text-center">ENTRAR</h1>
            <form className="mx-auto" onSubmit={handleUserLogin}>
                <div className="form-group">
                    <RoundedUser />
                    <input ref={inEmail} type="text" className="form-control " id="email" placeholder="Email" />
                    <div className="invalid-feedback">Digite seu email.</div>
                </div>
                <div className="form-group">
                    <Lock />
                    <input ref={inPassword} type="password" className="form-control" id="password" placeholder="Senha" />
                    <div className="invalid-feedback">Digite sua senha.</div>
                </div>
                <span id="sp-forgot-password">Esqueceu sua senha?</span>
                <LabeledButton type="submit" className="mx-auto d-block">
                    ENTRAR
                </LabeledButton>
            </form>
        </div>
    );
};

export default LoginPage;
