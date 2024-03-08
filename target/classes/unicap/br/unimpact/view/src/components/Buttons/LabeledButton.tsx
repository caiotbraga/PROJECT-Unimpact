import React from "react";

import { Button } from "react-bootstrap";

interface IButton {
    size?: string;
    type?: "button" | "submit" | "reset" | undefined;
    className?: string;
    secondary?: boolean;
    onClick?: () => void;
}

const LabeledButton: React.FC<IButton> = ({ size, type = "button", className = "", secondary, children, onClick }) => {
    return (
        <Button
            type={type}
            className={`${secondary ? "secondary" : "primary"}-button${size === "small" ? "-small" : ""} ${className}`}
            onClick={onClick}
        >
            {children}
        </Button>
    );
};

export default LabeledButton;
