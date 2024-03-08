import axios from "axios";
import React, { useContext, useEffect, useState } from "react";

import { AlertsContext } from "../../App";
import ConfirmationModal from "../ConfirmationModal";
import { getRequestBasicConfig } from "../../utils";
import { TNotification } from ".";

interface props {
    notification: TNotification;
    onCheckStatusChange: (notificationId: number, checked: boolean) => void;
}

const Notification: React.FC<props> = ({ notification, onCheckStatusChange }) => {
    const { addToast } = useContext(AlertsContext);

    const [checked, setChecked] = useState(false);
    const [showInviteModal, setShowInviteModal] = useState(false);

    const handleCheck = () => {
        setChecked(!checked);
    };

    const responseInvitation = (accept: boolean) => {
        let response;
        if (notification.type === "INVITE_GROUP") response = accept ? "ACCEPT_INVITE_GROUP" : "DECLINE_INVITE_GROUP";
        else response = accept ? "ACCEPT_INVITE_REPRESENTATIVE" : "DECLINE_INVITE_REPRESENTATIVE";
        axios
            .post(
                `http://localhost:9090/notification/invite/response?notificationId=${notification.id}&notificationType=${response}`,
                {},
                getRequestBasicConfig()
            )
            .then(() => {
                addToast({
                    header: "Sucesso!",
                    content: `O convite foi ${accept ? "aceito" : "recusado"}.`,
                    isSuccess: true,
                });
            })
            .catch(() => {
                addToast({
                    header: "Erro!",
                    content: `Ocorreu um erro ao ${
                        accept ? "aceitar" : "recusar"
                    } o convite. Tente novamente mais tarde.`,
                    isSuccess: false,
                });
            })
            .finally(() => {
                setShowInviteModal(false);
            });
    };

    const handleClick = (e: any) => {
        if (e.target.classList.contains("notification-checker")) return;

        if (notification.type === "INVITE_REPRESENTATIVE" || notification.type === "INVITE_GROUP") {
            setShowInviteModal(true);
        }
    };

    useEffect(() => {
        onCheckStatusChange(notification.id, checked);
    }, [checked]);

    return (
        <>
            <ConfirmationModal
                show={showInviteModal}
                title={"Deseja aceitar o convite?"}
                content={`${notification.message} Deseja aceitar?`}
                onCancel={() => responseInvitation(false)}
                cancelLabel="RECUSAR"
                onConfirm={() => responseInvitation(true)}
                confirmationLabel="ACEITAR"
                invertButtons
            />
            <div className="notification d-flex align-items-center justify-content-center" onClick={handleClick}>
                <div className="w-100">
                    <div className="notification-header">
                        <span className="notification-user m-0 text-truncate">{notification.senderName}</span>
                        <span className="notification-date mb-0">
                            {new Date(notification.createdAt).toLocaleString().split(",")[0]}
                        </span>
                    </div>
                    <p className="mb-0">{notification.message}</p>
                    <button className={`notification-checker ${checked && "selected"}`} onClick={handleCheck}></button>
                </div>
            </div>
        </>
    );
};

export default Notification;
