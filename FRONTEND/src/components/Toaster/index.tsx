import React from "react";

import { ToastContainer, Toast } from "react-bootstrap";

import { ReactComponent as ErrorIcon } from "../../resources/icons/toast_error_icon.svg";
import { ReactComponent as SuccessIcon } from "../../resources/icons/toast_success_icon.svg";

export type TToast = {
    header: string;
    content: string;
    isSuccess?: boolean;
};

interface props {
    toasts: TToast[];
    setToasts: (toasts: TToast[]) => void;
}

const Toaster: React.FC<props> = ({ toasts, setToasts }) => {
    const onCloseToast = (toastIndex: number) => {
        setToasts(toasts.filter((toast, index) => index !== toastIndex));
    };

    return (
        <ToastContainer
            className="h-100 p-3 d-flex flex-column justify-content-end"
            style={{ position: "fixed", top: "0", left: "0", pointerEvents: "none", zIndex: "1200" }}
        >
            {toasts.map((toast, i) => (
                <Toast key={i} bg="light" delay={10000} autohide onClose={() => onCloseToast(i)}>
                    <Toast.Header>
                        {toast.isSuccess === false ? <ErrorIcon /> : <SuccessIcon />}
                        <strong
                            className="me-auto"
                            style={{
                                color: `${toast.isSuccess === false ? "#A71616" : "#5B9A61"}`,
                                marginLeft: "8px",
                            }}
                        >
                            {toast.header}
                        </strong>
                        <small></small>
                    </Toast.Header>
                    <Toast.Body>
                        <p style={{ color: "#707070", marginBottom: "12px" }}>{toast.content}</p>
                    </Toast.Body>
                </Toast>
            ))}
        </ToastContainer>
    );
};

export default Toaster;
