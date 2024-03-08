export type TPhysicalUser = {
    addresses: TAddress[];
    cpf:       string;
    email:     string;
    name:      string;
    id:        number;
    phones:    TPhone[];
    rg:        string;
}

export interface TPostJuridicalUser {
    addresses:       TAddress[];
    cnpj:            string;
    fantasyName:       string;
    email:           string;
    institutionType: string;
    name:            string;
    phones:          TPhone[];
    password:        string;
}

export interface TJuridicalUser extends TPostJuridicalUser{
    id:              number;
}

export type TUserToBeUpdated = {
    addresses: TAddress[];
    cpf:       string;
    email:     string;
    name:      string;
    password:  string;
    phones:    TPhone[];
    rg:        string;
}

export type TAddress = {
    cep:      string;
    country:  string;
    district: string;
    city:     string;
    number:   string;
    state:    string;
    street:   string;
}

export type TPhone = {
    number: string;
}
