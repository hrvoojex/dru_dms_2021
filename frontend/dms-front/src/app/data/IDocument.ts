
export interface IDocumentResultSet {
    id: number;
    name: string;
    type: string;
    description: string;
    //document: Int32Array;
    document: string;
    path: string;
}

export interface IResultSet {
    message: string;
    result: IDocumentResultSet[];
}
