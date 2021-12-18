import { IFacebookValues } from 'app/shared/model/facebook-values.model';
import { FbType } from 'app/shared/model/enumerations/fb-type.model';

export interface IFacebook {
  id?: number;
  name?: string | null;
  url?: string | null;
  idOnFb?: string | null;
  type?: FbType | null;
  facebookValues?: IFacebookValues[] | null;
}

export const defaultValue: Readonly<IFacebook> = {};
