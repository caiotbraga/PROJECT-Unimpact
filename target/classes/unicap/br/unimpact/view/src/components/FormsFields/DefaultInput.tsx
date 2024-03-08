import React from "react";

interface props {
    id: string;
    label: string;
    size?: string;
    fRef?: React.MutableRefObject<HTMLInputElement | null>;
    placeholder?: string;
}

const DefaultInput: React.FC<props> = ({ fRef, id, label, size="fill", placeholder }) => {
    return (
        <div className="w-100 mb-3">
            <label htmlFor={id} className="default-input-label d-block mb-1">
                {label}
            </label>
            <input ref={fRef} id={id} type="text" placeholder={placeholder} className={`default-input ${size}-input w-100`}/>
        </div>
    );
};

export default DefaultInput;
