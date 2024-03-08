export type TNewGroup = {
    areasOfOperation: string[] | null;
    description: string | null;
    name: string;
    ownerUserEmail: string;
};

export interface TGroup {
    id: number;
    ownerUserEmail: string;
    name: string;
    description: string;
    members: TPhysicalUser[];
    areasOfOperation: string[];
}