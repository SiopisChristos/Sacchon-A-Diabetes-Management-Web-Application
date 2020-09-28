export interface Note {
    id: number;
    message: string;
    date: Date;
    patientLastname: string;
    doctorLastname: string;
    patient_id: number;
    doctor_id: number;
    seen: boolean;  
}
