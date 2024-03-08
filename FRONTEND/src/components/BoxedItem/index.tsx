import React from "react";

interface props {
    href?: string;
    className?: string;
    title: string;
    rightContent?: any;
    variant?: "container";
    onClick?: () => void;
}

const BoxedItem: React.FC<props> = ({ href, className = "", title, rightContent, variant, onClick }) => {
    const children = (
        <>
            <div className="__left-floating-content">
                <span>{title}</span>
            </div>
            <div className="__right-floating-content">{rightContent}</div>
        </>
    );
    
    if (variant === "container")
        return (
            <div className={`full-width-box boxed-item ${className}`} onClick={onClick}>
                {children}
            </div>
        );
    
    return (
        <a href={href} className={`full-width-box boxed-item ${className}`}>
            {children}
        </a>
    );
};

export default BoxedItem;
