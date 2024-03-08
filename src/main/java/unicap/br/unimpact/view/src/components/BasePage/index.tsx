import React from "react";

interface props {
    id?: string;
    classname?: string
}

const BasePage: React.FC<props> = ({children, id, classname}) => {
    return (
        <div id={id} className={`page ${classname}`}>
            <div className="max-width-content mx-auto">
                {children}
            </div>
        </div>
    );
};

export default BasePage;
