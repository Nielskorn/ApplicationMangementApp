import {DashViewApplication} from "../Component/DashViewApplication.tsx";

export default function HomePage(){

    return(
<div>
    <h1>Welcome User</h1>
    <div className="ApplicationDash">
        <h2>Applications with next ReminderDate </h2>
    <DashViewApplication/>
    </div>
</div>)
}