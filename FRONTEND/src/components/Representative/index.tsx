import React from "react";

import { ReactComponent as RemoveIcon } from "../../resources/icons/remove_icon.svg";

import { TJuridicalUser, TPhysicalUser } from "../../types/UserTypes";

interface props {
    representative: TPhysicalUser | TJuridicalUser;
    setShowDeleteUserModal: (show: boolean) => void;
    setRepresentativeToBeRemoved: (email: string) => void;
    canBeRemoved?: boolean;
    isOwner?: boolean
}

const Representative: React.FC<props> = ({
    representative,
    setShowDeleteUserModal,
    setRepresentativeToBeRemoved,
    canBeRemoved,
    isOwner
}) => {
    return (
        <tr>
            <td className="td-primary">
                <div>{`${representative.name}${isOwner ? ' (Proprietário)' : ''}`}</div>
            </td>
            <td className="td-secondary d-flex align-items-center justify-content-between">
                <div>
                    <a href={`mailto:${representative.email}`}>{representative.email}</a>
                    {canBeRemoved && (
                        <RemoveIcon
                            className="remove-button"
                            onClick={() => {
                                setRepresentativeToBeRemoved(representative.email);
                                setShowDeleteUserModal(true);
                            }}
                        />
                    )}
                </div>
            </td>
        </tr>
    );
};

export default Representative;
