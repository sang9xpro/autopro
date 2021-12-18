import { ISchedulerTask } from 'app/shared/model/scheduler-task.model';
import { ISchedulerTaskFields } from 'app/shared/model/scheduler-task-fields.model';

export interface ISchedulerTaskValues {
  id?: number;
  value?: string | null;
  schedulerTask?: ISchedulerTask | null;
  schedulerTaskFields?: ISchedulerTaskFields | null;
}

export const defaultValue: Readonly<ISchedulerTaskValues> = {};
