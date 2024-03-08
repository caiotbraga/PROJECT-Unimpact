import axios, { AxiosResponse } from "axios";
import { TNewGroup } from "../types/GroupTypes";
import { getRequestBasicConfig } from "../utils";

export const createGroup = (
    newGroup: TNewGroup,
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .post(
            "http://localhost:9090/group/",
            {
                ...newGroup,
            },
            getRequestBasicConfig()
        )
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchGroupsByOwner = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get("http://localhost:9090/group/get/by/owner", getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchUNICAPGroups = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get("http://localhost:9090/group/get/departments", getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchGroupsByParticipate = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get("http://localhost:9090/group/get/by/participate", getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchGroupById = (
    groupId: number,
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get(`http://localhost:9090/group/${groupId}`, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchGroupProjects = (
    groupId: number,
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get(`http://localhost:9090/project/by/group?groupId=${groupId}`, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const deleteGroupById = (
    groupId: number,
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .delete(`http://localhost:9090/group/${groupId}`, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const removeGroupMember = (
    data: any,
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .delete(`http://localhost:9090/group/member`, getRequestBasicConfig(data))
        .then(successCallback)
        .catch(errorCallback);
};
