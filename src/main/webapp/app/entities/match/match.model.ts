import { BaseEntity } from './../../shared';

export class Match implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public duration?: number,
        public resultMatch?: BaseEntity,
        public teamList?: BaseEntity,
        public tournament?: BaseEntity,
        public map?: BaseEntity,
        public matchModes?: BaseEntity[],
    ) {
    }
}
