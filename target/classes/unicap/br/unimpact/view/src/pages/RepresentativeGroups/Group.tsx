import React from "react";
import BoxedItem from "../../components/BoxedItem";
import { TGroup } from "../../types/GroupTypes";

interface props {
    group: TGroup;
    setOpenedGroup: (group: TGroup) => void;
}

const Group: React.FC<props> = ({group, setOpenedGroup}) => {

    return (
        <BoxedItem title={group.name} onClick={() => setOpenedGroup(group)} variant="container" />
    );
};

export default Group;
