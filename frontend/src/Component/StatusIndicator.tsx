import * as React from "react";

interface StatusIndicatorProps {
    status: "OPEN" | "CLOSED" | "IN_PROGRESS" | "SUCCESS" | string
}

function getStatusColor(status: StatusIndicatorProps["status"]) {
    switch (status) {
        case "OPEN":
            return "green"
        case  "IN_PROGRESS":
            return "orange"
        case "SUCCESS":
            return "blue"
        case "CLOSED":
            return "red"
        default:
            return ""
    }
}

const StatusIndicator: React.FC<StatusIndicatorProps> = ({status}) => {

    return (
        <div className="flex items-center space-x-2"
             style={{display: "flex", justifyContent: "center", alignItems: "center", gap: "8px"}}>
            <div
                style={{width: "16px", height: "16px", borderRadius: "50%", backgroundColor: getStatusColor(status)}}
                title={status}
            ></div>
            <span className="text-sm font-medium capitalize">{status}</span>
        </div>
    );
};
export default StatusIndicator
