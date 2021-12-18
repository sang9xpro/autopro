import { IComments } from 'app/shared/model/comments.model';
import { ICommentsFields } from 'app/shared/model/comments-fields.model';

export interface ICommentsValues {
  id?: number;
  value?: string | null;
  comments?: IComments | null;
  commentsFields?: ICommentsFields | null;
}

export const defaultValue: Readonly<ICommentsValues> = {};
