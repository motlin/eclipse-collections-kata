/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.companykata

import org.eclipse.collections.api.list.MutableList
import org.eclipse.collections.impl.list.mutable.FastList
import org.eclipse.collections.impl.test.Verify
import org.junit.{Assert, Test}

class ScalaExercise1Test extends CompanyDomainForKata
{
    @Test
    def getCustomerNames(): Unit =
    {
        // Inference on lambda's parameter, and on collect's result
        val customerNames: MutableList[String] = this.company.getCustomers.collect(_.getName)

        // Inference on lambda's parameter, and on return type
        val customerNames2 = this.company.getCustomers.collect[String](_.getName)

        // Inference on collect's result and return type, doesn't work
        // Compiler error: no type parameters for method collect ... exist so that it can be applied to arguments Function[Cusomter, String]
        // Error contains Note: Customer <: Any, but Java-defined trait Function is invariant in type T.
        // val customerNames = this.company.getCustomers.collect((customer: Customer) => customer.getName)

        // Inference on all three doesn't work of course, with same compiler error
        // val customerNames = this.company.getCustomers.collect(_.getName)

        // Note: Collect has no overloads of the same arity
        // Note: Collect uses bounded wildcards, which seems to interfere with inference somewhat

        val expectedNames: MutableList[String] = FastList.newListWith("Fred", "Mary", "Bill")
        Assert.assertEquals(expectedNames, customerNames)
        Assert.assertEquals(expectedNames, customerNames2)
    }

    @Test
    def getLondonCustomers(): Unit =
    {
        // select is the same as filter
        // selectWith is a two argument form which works well with method references in Java
        // MutableList<Customer> customersFromLondon = customers.selectWith(Customer::livesIn, "London");

        // Lots of type inference works, if we provide the type of city, the second labmda parameter
        val customersFromLondon = this.company.getCustomers.selectWith((customer, city: String) => customer.livesIn(city), "London")

        // We can infer the lambda types if we specify the type on selectWith
        val customersFromLondon2 = this.company.getCustomers.selectWith[String](_ livesIn _, "London")

        // Adding the return type doesn't help with inference, because it's String which cannot be inferred, not Customer
        // val customersFromLondon: MutableList[Customer] = this.company.getCustomers.selectWith((customer, city) => customer.livesIn(city), "London")
        // Compiler error: missing parameter type
        // Points to city, so it's very clear

        Verify.assertSize("Should be 2 London customers", 2, customersFromLondon)
    }
}
