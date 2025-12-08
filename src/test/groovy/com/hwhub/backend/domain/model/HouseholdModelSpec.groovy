package com.hwhub.backend.domain.model

import spock.lang.Specification

class HouseholdModelSpec extends Specification {

    // =========================
    // reconstruct
    // =========================

    def "reconstructは全プロパティをそのまま保持する"() {
        given:
        Long householdId = 1L
        String name = "test-household"
        Long ownerUserId = 99L

        when:
        def model = HouseholdModel.reconstruct(householdId, name, ownerUserId)

        then:
        model.householdId == householdId
        model.name == name
        model.ownerUserId == ownerUserId
    }

    // =========================
    // create
    // =========================

    def "createはhouseholdIdがnullで生成される"() {
        given:
        String name = "new-household"
        Long ownerUserId = 10L

        when:
        def model = HouseholdModel.create(name, ownerUserId)

        then:
        model.householdId == null
        model.name == name
        model.ownerUserId == ownerUserId
    }

    // =========================
    // isOwner
    // =========================

    def "isOwnerはownerUserIdと一致する場合trueを返す"() {
        given:
        Long ownerUserId = 5L
        def model = HouseholdModel.reconstruct(1L, "house", ownerUserId)

        expect:
        model.isOwner(ownerUserId) == true
    }

    def "isOwnerはownerUserIdと一致しない場合falseを返す"() {
        given:
        def model = HouseholdModel.reconstruct(1L, "house", 5L)

        expect:
        model.isOwner(999L) == false
    }

    def "isOwnerはnullと比較してもfalseを返す"() {
        given:
        def model = HouseholdModel.reconstruct(1L, "house", 5L)

        expect:
        model.isOwner(null) == false
    }

    // =========================
    // changeName
    // =========================

    def "changeNameはnameを上書きする"() {
        given:
        def model = HouseholdModel.reconstruct(1L, "old-name", 10L)

        when:
        model.changeName("new-name")

        then:
        model.name == "new-name"
    }
}
