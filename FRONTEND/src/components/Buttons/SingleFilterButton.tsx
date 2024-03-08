import React from "react";

import { FilterList } from "@material-ui/icons";

interface props {
    label: string;
    onClick: (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
}

const SingleFilterButton: React.FC<props> = ({ label, onClick }) => {
    return (
        <>
        <span className="d-block medium-text">Filtrar por:</span>
            <button onClick={onClick} className="__filter d-flex align-items-center secondary-text mb-3 p-0">
                <FilterList />
                <span className="lh-1 ms-2">{label}</span>
            </button>
        </>
    );
};

export default SingleFilterButton;
