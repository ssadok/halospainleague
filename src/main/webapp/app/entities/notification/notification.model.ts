import { BaseEntity } from './../../shared';

const enum NotificationType {
    'NOTIFICATION',
    'INVITATION',
    'ALERT'
}

export class Notification implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: any,
        public creation?: any,
        public type?: NotificationType,
        public token?: string,
        public read?: boolean,
        public player?: BaseEntity,
    ) {
        this.read = false;
    }
}
