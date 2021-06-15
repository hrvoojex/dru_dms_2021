
export interface IDocumentResultSet {
    id: number;
    name: string;
    type: string;
    description: string;
    document: Int32Array;
    path: string;
}

export interface IResultSet {
    message: string;
    result: IDocumentResultSet[];
}

export interface IDocument {
    name: string;
    description: string;
    document: Int32Array;
}