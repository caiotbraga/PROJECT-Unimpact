import React, { useEffect, useState } from "react";

import Notification from "./Notification";

import { Delete } from "@material-ui/icons";

import { ReactComponent as NotificationsBellIcon } from "../../resources/icons/notification_bell.svg";

import { getRequestBasicConfig } from "../../utils";
import axios from "axios";

export interface TNotification {
    id: number;
    message: string;
    type: string;
    senderEmail: string;
    senderName: string;
    recipientEmail: string;
    recipientName: string;
    status: string;
    createdAt: string;
}

const Notifications: React.FC = () => {
    const [showNotifications, setShowNotifications] = useState(false);
    const [notifications, setNotifications] = useState<TNotification[]>([]);
    const [selectedNotifications, setSelectedNotifications] = useState<number[]>([]);

    const handleNotificationCheckStatusChange = (notificationId: number, checked: boolean) => {
        let auxSelected: number[] = JSON.parse(JSON.stringify(selectedNotifications));
        if (checked) auxSelected.push(notificationId);
        else auxSelected = auxSelected.filter((n) => n !== notificationId);

        setSelectedNotifications(auxSelected);
    };

    const fetchNotifications = () => {
        axios
            .get(`http://localhost:9090/notification/received/`, getRequestBasicConfig())
            .then((response) => {
                setNotifications(response.data.object);
            })
            .catch((error) => {
                if (error.response.status === 400) setNotifications([]);
            });

        setTimeout(fetchNotifications, 2500);
    };

    const removeNotifications = () => {
        if (selectedNotifications.length === 0) removeAllNotifications();
        else {
            axios
                .delete(
                    `http://localhost:9090/notification/?notifications=${selectedNotifications.join(",")}`,
                    getRequestBasicConfig()
                )
                .then(() => {
                    setSelectedNotifications([]);
                    fetchNotifications();
                })
                .catch((error) => {
                    if (error.response.status === 400) setNotifications([]);
                });
        }
    };

    const removeAllNotifications = () => {
        axios
            .delete(`http://localhost:9090/notification/received/`, getRequestBasicConfig())
            .then(() => {
                setNotifications([]);
            })
            .catch((error) => {
                if (error.response.status === 400) setNotifications([]);
            });
    };

    useEffect(() => {
        window.onclick = (e: any) => {
            if(e.target?.id === 'notifications_close_div') setShowNotifications(false)
        }
        fetchNotifications();

        return () => {
            window.onclick = null
        }
    }, []);

    return (
        <div id="notifications" className="d-flex align-items-center justify-content-center me-4">
            <button
                id="btn-notifications"
                className={`${notifications.length && "has-notifications"}`}
                onClick={() => setShowNotifications(!showNotifications)}>
                <NotificationsBellIcon id="notifications-bell" className="main-icon" />
            </button>
            {showNotifications && (
                <>
                <div id="notifications_close_div"></div>
                <div id="notifications-container" className="shadow-box-element">
                    <div>
                        <div id="indicator"></div>
                        <div id="notifications-header" className="d-flex align-items-center">
                            <p id="notifications-container-title" className="m-0">
                                Notificações
                            </p>
                            <button onClick={removeNotifications} disabled={notifications.length === 0}>
                                <Delete />
                                <span>{`Excluir ${selectedNotifications.length > 0 ? "selecionadas" : "todas"}`}</span>
                            </button>
                        </div>
                        <div id="_notifications">
                            {notifications.length === 0 ? (
                                <span id="no-notifications-message">Nada por aqui.</span>
                            ) : (
                                ""
                            )}
                            {notifications.map((notification) => (
                                <Notification
                                    key={notification.id}
                                    notification={notification}
                                    onCheckStatusChange={handleNotificationCheckStatusChange}
                                />
                            ))}
                        </div>
                    </div>
                </div>
                </>
            )}
        </div>
    );
};

export default Notifications;
