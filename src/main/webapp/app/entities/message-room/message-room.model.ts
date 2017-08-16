import { BaseEntity } from './../../shared';

export class MessageRoom implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public imageContentType?: string,
        public image?: any,
        public coverContentType?: string,
        public cover?: any,
        public crated?: any,
        public isPublic?: boolean,
        public themes?: BaseEntity[],
        public message?: BaseEntity,
        public players?: BaseEntity[],
    ) {
        this.isPublic = false;
    }
}
