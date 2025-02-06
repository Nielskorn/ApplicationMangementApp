import * as React from "react";

interface StatusIndicatorProps{
    status:"OPEN"|"CLOSED"|"IN_PROGRESS"|"SUCCESS"|string
}
function  getStatusColor(status: StatusIndicatorProps["status"]){
    switch (status) {
        case "OPEN":
            return "bg-green-500"
        case  "IN_PROGRESS":
            return "bg-orange-500"
        case "SUCCESS":
            return "bg-blue-500"
        case "CLOSED":
            return "bg-red-500"
        default:
            return "bg-gray-500"
    }
}
const StatusIndicator:React.FC<StatusIndicatorProps>=({status})=>{

return (
    <div className="flex items-center space-x-2">
        <div
            className={`w-4 h-4 rounded-full ${getStatusColor(status)}`}
            title={status}
        ></div>
        <span className="text-sm font-medium capitalize">{status}</span>
    </div>
);
};
export default StatusIndicator
