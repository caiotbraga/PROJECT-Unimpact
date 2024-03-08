import React from "react";

interface props {
    id: string;
    placeholder?: string;
    label: string;
    required?: boolean;
    className?: string;
    defaultValue?: string | undefined;
    fieldType?: string;
    inputSize?: string;
    invalidFeedback?: string;
    isReadOnly?: boolean;
    parentClassName?: string;
    fRef?: React.MutableRefObject<HTMLInputElement | null>;
    disableAutoComplete?: boolean;
    isInvalid?: boolean;
}

const FormFloatingInput: React.FC<props> = ({
    defaultValue,
    id,
    required,
    invalidFeedback,
    placeholder,
    label,
    className = "",
    disableAutoComplete = false,
    fieldType = "text",
    fRef,
    inputSize = "medium",
    isReadOnly = false,
    parentClassName = "",
    isInvalid,
}) => {
    return (
        <div className={`form-input-variant col-md-auto ${inputSize}-input ${parentClassName}`}>
            <label htmlFor={id} className={`${required ? "required" : ""}`}>
                {label}
            </label>
            {fieldType !== "date" && (
                <input
                    ref={fRef}
                    type={fieldType}
                    className={`form-control ${className} ${isInvalid ? "is-invalid" : ""}`}
                    id={id}
                    placeholder={placeholder}
                    defaultValue={defaultValue}
                    readOnly={isReadOnly}
                    autoComplete={disableAutoComplete ? "off" : ""}
                />
            )}
            {fieldType === "date" && (
                <input
                    ref={fRef}
                    type="date"
                    id={id}
                    className={className}
                    readOnly={isReadOnly}
                    defaultValue={defaultValue}
                />
            )}
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

export default FormFloatingInput;
