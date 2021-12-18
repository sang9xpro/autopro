import { ICommentsValues } from 'app/shared/model/comments-values.model';
import { IApplications } from 'app/shared/model/applications.model';

export interface IComments {
  id?: number;
  content?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  owner?: string | null;
  commentsValues?: ICommentsValues[] | null;
  applications?: IApplications | null;
}

export const defaultValue: Readonly<IComments> = {};
