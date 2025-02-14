import {Application} from "./Application.ts";
import {JobOffer} from "./JobOffer.ts";

export type JobApplicationTracker = {
    jobOffer: JobOffer
    application: Application

}
