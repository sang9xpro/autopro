import { ICommentsValues } from 'app/shared/model/comments-values.model';

export interface ICommentsFields {
  id?: number;
  fieldName?: string | null;
  commentsValues?: ICommentsValues[] | null;
}

export const defaultValue: Readonly<ICommentsFields> = {};
