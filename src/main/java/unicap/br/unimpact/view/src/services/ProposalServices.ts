import axios, { AxiosResponse } from "axios";
import { getRequestBasicConfig } from "../utils";

export const postProposal = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    data: any,
    errorCallback: (error: any) => void
) => {
    axios
        .post("http://localhost:9090/project/register", { ...data }, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchProposal = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    proposalId: number,
    errorCallback: (error: any) => void
) => {
    axios
        .get(`http://localhost:9090/project/${proposalId}`, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const updateProposalStatus = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    data: any,
    errorCallback: (error: any) => void
) => {
    axios
        .post("http://localhost:9090/status/", { ...data }, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchAllProposals = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get("http://localhost:9090/project/all", getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const fetchAssignedProposals = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    errorCallback: (error: any) => void
) => {
    axios
        .get("http://localhost:9090/project/by/responsible/university", getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const getProposalNextStatus = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    statusId: number,
    errorCallback: (error: any) => void
) => {
    axios
        .get(`http://localhost:9090/status/next/${statusId}`, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};

export const getProposalLastStatus = (
    successCallback: (response: AxiosResponse<any, any>) => void,
    statusId: number,
    errorCallback: (error: any) => void
) => {
    axios
        .get(`http://localhost:9090/status/last/${statusId}`, getRequestBasicConfig())
        .then(successCallback)
        .catch(errorCallback);
};