import { BaseEntity } from './../../shared';

export class TeamList implements BaseEntity {
    constructor(
        public id?: number,
        public reference?: string,
        public score?: number,
        public match?: BaseEntity,
        public teams?: BaseEntity[],
    ) {
    }
}
