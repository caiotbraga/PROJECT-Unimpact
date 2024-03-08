export const getUserAuth = (): string => {
    const userAuth = window.sessionStorage.getItem("user_auth");

    if (!userAuth) {
        window.location.href = "/";
        return "";
    }

    return userAuth;
};

export const handleLogOutUser = () => {
    window.sessionStorage.removeItem("user_auth");
    window.sessionStorage.removeItem("user_type");

    window.location.href = "";
};

export const getRequestBasicConfig = (data?: any) => {
    if (data) {
        return {
            headers: {
                Authorization: getUserAuth(),
            },
            data: data,
        };
    }

    return {
        headers: {
            Authorization: getUserAuth(),
        },
    };
};
