import {Note} from "../types/Note.ts";
import {useEffect, useState} from "react";
import axios from "axios";

export default function NoteLine(note: Readonly<Note>) {
    const [id, setId] = useState<string>()
    useEffect(() => {
        setId(note.id)
    }, []);

    function deleteNote() {
        axios.delete("/api/note/" + id)
    }

    return (<li key={id}>
        {note.notes}
        <button onClick={deleteNote}>delete</button>
    </li>)
}