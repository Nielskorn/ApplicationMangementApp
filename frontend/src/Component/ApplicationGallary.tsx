import {Application} from "../types/Application.ts";
import JobApplicationCard from "./JobApplicationCard.tsx";


type ApplicationGalleryProps={
    applications:Application[]


}
export default function ApplicationGallery({applications}: Readonly<ApplicationGalleryProps>){
    const applicationCards=applications.map((application:Application)=>(<div className="applicationCard" key={application.id}>
        <JobApplicationCard application={application}/>

    </div>));

    return(<>

        {applicationCards}

    </>)
}