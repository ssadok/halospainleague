import { BaseEntity } from './../../shared';

const enum AchievementType {
    'BADGE',
    'TROPHY'
}

export class Achievement implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: any,
        public score?: number,
        public type?: AchievementType,
        public players?: BaseEntity[],
    ) {
    }
}
