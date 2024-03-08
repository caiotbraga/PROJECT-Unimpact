import React from "react";

interface props {
    title: string;
    className?: string;
}

const PageTitle: React.FC<props> = ({ children, title, className }) => {
    return (
        <div className="page-title">
            <p className={`_title main-text ${className}`}>{title}</p>
            {children}
        </div>
    );
};

export default PageTitle;
