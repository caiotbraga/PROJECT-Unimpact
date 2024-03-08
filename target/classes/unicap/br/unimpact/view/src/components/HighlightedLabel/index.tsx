import React from "react";

import { ReactComponent as PlayIcon } from "../../resources/icons/white_play.svg";

const HighlightedLabel: React.FC<{ label: string, size?: "default" | "small" }> = ({ label, size = "default" }) => {
    return (
        <div className={`highlighted-label-${size}`}>
            <PlayIcon className="highlighted-icon"/>
            <span>{label}</span>
        </div>
    );
};

export default HighlightedLabel;
