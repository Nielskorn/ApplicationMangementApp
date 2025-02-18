import {Application} from "./Application.ts";
import {JobOffer} from "./JobOffer.ts";
import {Note} from "./Note.ts";

export type JobApplicationTracker = {
    jobOffer: JobOffer
    application: Application
    notes: Note[]

}
