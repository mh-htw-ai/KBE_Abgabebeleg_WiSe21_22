
describe('MwSt-API test cases', () =>{
    it('calculate test from artikelpreis with standard steuertype', () =>{
        cy.request({
            method : 'PUT',
            url : 'http://localhost:21111/mwst/api/v1.0/calculate_json',
            body : {
                "artikelpreis": 100.0,
                "steueranteil": 0,
                "artMitSteuer": 0,
                "steuertyp":"standard"
            }
        }).then((res) => {
            expect(res.status).to.eq(200)
            expect(res.body).has.property('artikelpreis', 100.0)
            expect(res.body).has.property('steueranteil', 19.0)
            expect(res.body).has.property('artMitSteuer', 119.0)
            expect(res.body).has.property('steuertyp', 'standard')
        })
    })

    it('calculate test from artMitSteuer with standard steuertype', () =>{
        cy.request({
            method : 'PUT',
            url : 'http://localhost:21111/mwst/api/v1.0/calculate_json',
            body : {
                "artikelpreis": 0,
                "steueranteil": 0,
                "artMitSteuer": 107.0,
                "steuertyp":"reduziert"
            }
        }).then((res) => {
            expect(res.status).to.eq(200)
            expect(res.body).has.property('artikelpreis', 100.0)
            expect(res.body).has.property('steueranteil', 7.0)
            expect(res.body).has.property('artMitSteuer', 107.0)
            expect(res.body).has.property('steuertyp', 'reduziert')
        })
    })

    it('calculate test with not enough values', () =>{
        cy.request({
            method : 'PUT',
            url : 'http://localhost:21111/mwst/api/v1.0/calculate_json',
            failOnStatusCode : false,
            body : {
                "artikelpreis": 0,
                "steueranteil": 0,
                "artMitSteuer": 0,
                "steuertyp":"standard"
            }
        }).then((res) => {
            expect(res.status).to.eq(400)
        })
    })

    it('calculate test with no steuertype', () =>{
        cy.request({
            method : 'PUT',
            url : 'http://localhost:21111/mwst/api/v1.0/calculate_json',
            failOnStatusCode : false,
            body : {
                "artikelpreis": 0,
                "steueranteil": 0,
                "artMitSteuer": 119
            }
        }).then((res) => {
            expect(res.status).to.eq(400)
        })
    })
})