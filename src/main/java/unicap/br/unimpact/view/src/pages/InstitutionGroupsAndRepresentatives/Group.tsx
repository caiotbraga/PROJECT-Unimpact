import React from "react";
import { TGroup } from "../../types/GroupTypes";

interface props {
    group: TGroup;
    setOpenedGroup: (group: TGroup) => void;
}

const Group: React.FC<props> = ({ group, setOpenedGroup}) => {
    const handleGroupClick = (e: any) => {
        if (!e.target.classList.contains("remove-button")) setOpenedGroup(group);
    };

    return (
        <tr onClick={handleGroupClick}>
            <td className="td-primary">
                <div>{group.name}</div>
            </td>
            <td className="td-secondary d-flex align-items-center justify-content-between">
                <div>
                    <span>{group.members.length}</span>
                </div>
            </td>
        </tr>
    );
};

export default Group;
