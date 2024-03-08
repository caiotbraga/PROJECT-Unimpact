import { TPhysicalPersonUser } from "../pages/UserRegister/RegisterPhysicalPerson";

export interface TProposal {
    actualState:     ActualState;
    createdAt:       string;
    id:              number;
    metadataProject: MetadataProject;
    modifiedAt:      string;
    new:             boolean;
    ownerEmail:      string;
    responsible:     Responsible;
}

export interface ActualState {
    actualStatus: ActualStatus;
    createdAt:    string;
    id:           number;
    modifiedAt:   string;
    state:        string;
}

export interface ActualStatus {
    assignedEmail: string;
    createdAt:     string;
    id:            number;
    modifiedAt:    string;
    responsible:   Responsible;
    status:        string;
}

export interface Responsible {
    groupsResponsibles:        GroupsResponsible[];
    id:                        number;
    physicalPersonResponsible: TPhysicalPersonUser;
}

export interface GroupsResponsible {
    areasOfOperation: string[];
    description:      string;
    id:               number;
    members:          TPhysicalPersonUser[];
    name:             string;
    ownerUserEmail:   string;
}

export interface MetadataProject {
    finishDate:          string;
    id:                  number;
    mainObjective:       string;
    modifiedAt:          string;
    numberBeneficiaries: number;
    specificObjectives:  string[];
    startDate:           string;
    summary:             string;
    targetPublic:        string[];
    title:               string;
}
