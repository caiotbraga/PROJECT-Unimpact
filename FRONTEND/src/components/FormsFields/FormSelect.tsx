import React from "react";

interface props {
    options: string[];
    values?: string[];
    defaultOptionValue?: string;
    label: string;
    invalidFeedback?: string;
    fRef?: React.MutableRefObject<HTMLSelectElement | null>;
    id?: string;
    isDisabled?: boolean;
    required?: boolean;
    size?: string;
    isInvalid?: boolean;
    onChange?: (e: React.ChangeEvent<HTMLSelectElement>) => void;
}

const FormSelect: React.FC<props> = ({
    options,
    values,
    defaultOptionValue = "Selecione",
    label,
    fRef,
    id,
    size = "medium",
    isDisabled,
    required,
    isInvalid,
    invalidFeedback,
    onChange
}) => {
    return (
        <div className={`form-input-variant col-md-auto ${size}-input`}>
            <label htmlFor={id} className={`${required ? "required" : ""}`}>
                {label}
            </label>
            <select
                ref={fRef}
                id={id}
                className={`form-select no-outline-focus ${isInvalid && "is-invalid"}`}
                disabled={isDisabled}
                required={required}
                onChange={onChange}
            >
                <option value="none">{defaultOptionValue}</option>
                {options.map((opt, i) => (
                    <option key={i} value={values ? values[i].toLowerCase() : opt.toLowerCase()}>
                        {opt}
                    </option>
                ))}
            </select>
            {invalidFeedback || required ? (
                <div className="invalid-feedback">
                    {required && !invalidFeedback ? "Este campo é obrigatório." : invalidFeedback}
                </div>
            ) : (
                ""
            )}
        </div>
    );
};

export default FormSelect;
