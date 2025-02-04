import {Application} from "../types/Application.ts";
import ApplicationCard from "./ApplicationCard.tsx";


type ApplicationGalleryProps={
    applications:Application[]


}
export default function ApplicationGallery({applications}: Readonly<ApplicationGalleryProps>){
    const applicationCards=applications.map((application:Application)=>(<div  key={application.id}>
        <ApplicationCard application={application}/>

    </div>));

    return(<>

        {applicationCards}

    </>)
}